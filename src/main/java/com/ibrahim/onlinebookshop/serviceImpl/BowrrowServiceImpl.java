package com.ibrahim.onlinebookshop.serviceImpl;

import com.ibrahim.onlinebookshop.dto.BookDto;
import com.ibrahim.onlinebookshop.dto.BowrrowDto;
import com.ibrahim.onlinebookshop.dto.BowrrowInfoDto;
import com.ibrahim.onlinebookshop.exceptions.AlreadyOwned;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.ResourceNotFoundException;
import com.ibrahim.onlinebookshop.exceptions.UnAuthorizedPeople;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Bowrrow;
import com.ibrahim.onlinebookshop.model.Reserve;
import com.ibrahim.onlinebookshop.model.UserEntity;
import com.ibrahim.onlinebookshop.repository.BookRepo;
import com.ibrahim.onlinebookshop.repository.BowrrowRepo;
import com.ibrahim.onlinebookshop.repository.ReserveRepo;
import com.ibrahim.onlinebookshop.repository.UserRepo;
import com.ibrahim.onlinebookshop.service.BowrrowService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BowrrowServiceImpl implements BowrrowService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BowrrowRepo bowrrowRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ReserveRepo reserveRepo;

    @Override
    public BowrrowDto bookBorrowing(int bookId) throws InvalidBookId, AlreadyOwned {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity>user=userRepo.findByEmail(authentication.getName());
        int userId=user.get().getUserId();
        UserEntity user1=userRepo.findByUserId(userId);
        Book book=bookRepo.findByBookId(bookId);
        if(book==null)throw new InvalidBookId("Book Id is Invalid");
        if(book.getIsAvailable().equals("OCCUPIED")) throw new AlreadyOwned("Currently Unavailable");
        Bowrrow bowrrow=new Bowrrow();
        bowrrow.setBook(book);
        bowrrow.setUserEntity(user1);
        bowrrow.setBowrrowDate(LocalDate.now());
        bowrrow.setDuedate(LocalDate.now().plusDays(7));
        bowrrow.getBook().setIsAvailable("OCCUPIED");
        bowrrowRepo.save(bowrrow);
        return modelMapper.map(bowrrow, BowrrowDto.class);

    }

    @Override
    public BowrrowDto bookReturning(int bookId) throws InvalidBookId {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity>user=userRepo.findByEmail(authentication.getName());
        int userId=user.get().getUserId();
        UserEntity user1=userRepo.findByUserId(userId);
        Book book=bookRepo.findByBookId(bookId);
        if(book==null)throw new InvalidBookId("Invalid Book Id");

        Reserve reserve=reserveRepo.findByBook(book);
        if(reserve!=null) {
            reserve.setReservationStatus("Available");
        }
        Bowrrow bowrrow=bowrrowRepo.findByUserEntityAndBookAndReturnDateIsNull(user1,book);
        bowrrow.setReturnDate(LocalDate.now());
        bowrrow.getBook().setIsAvailable("AvailAble");
        bowrrowRepo.save(bowrrow);
        return modelMapper.map(bowrrow,BowrrowDto.class);
    }

    @Override
    public List<BookDto> getAllBookByUser(int userId) throws ResourceNotFoundException,UnAuthorizedPeople {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity>user=userRepo.findByEmail(authentication.getName());
        int userId1=user.get().getUserId();
        String role=user.get().getRole();
        UserEntity userEntity1=userRepo.findByUserId(userId);

        if(userEntity1==null)throw new ResourceNotFoundException("User","id",userId);

        if(userId1!=userId && role.equals("CUSTOMER")) throw new UnAuthorizedPeople("Access Restricted");
        UserEntity userEntity=userRepo.findByUserId(userId);
        List<Bowrrow>bowrrows=bowrrowRepo.findAllByUserEntity(userEntity);

       List<Book>books=new ArrayList<>();
       for(Bowrrow bowrrow:bowrrows)
       {
           books.add(bowrrow.getBook());
       }
       return books.stream().map((todo) -> modelMapper.map(todo, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BowrrowInfoDto> getUserAllHistory(int userId) throws InvalidBookId,UnAuthorizedPeople {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByEmail(authentication.getName());
        String currentUserRole = user.get().getRole();
        int currentUserId = user.get().getUserId();

        UserEntity userEntity1=userRepo.findByUserId(userId);

        if(userEntity1==null)throw new ResourceNotFoundException("User","id",userId);
        if (currentUserId!=userId && currentUserRole.equals("CUSTOMER")) {
            throw new UnAuthorizedPeople("You are not authorized to access this!");
        }
        UserEntity userEntity = userRepo.findByUserId(userId);
        List<Bowrrow> bookBorrowings = bowrrowRepo.findAllByUserEntity(userEntity);

      List<BowrrowInfoDto>bowrrowInfoDtos=new ArrayList<>();
      for(Bowrrow bowrrow:bookBorrowings)
      {
          BowrrowInfoDto bowrrowInfoDto=new BowrrowInfoDto();
          bowrrowInfoDto.setBorrowId(bowrrow.getBowrrow_Id());
          bowrrowInfoDto.setReturnDate(bowrrow.getReturnDate());
          bowrrowInfoDto.setBookEntity(bowrrow.getBook().getBookId());
          bowrrowInfoDto.setDueDate(bowrrow.getDuedate());
          bowrrowInfoDto.setBorrowDate(bowrrow.getBowrrowDate());
          bowrrowInfoDtos.add(bowrrowInfoDto);
      }

     return bowrrowInfoDtos;

    }

    public List<BookDto> getAllBorrowedBookByUser(int userId) throws ResourceNotFoundException,UnAuthorizedPeople {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByEmail(authentication.getName());
        String currentUserRole = user.get().getRole();
        int currentUserId = user.get().getUserId();

        UserEntity userEntity1=userRepo.findByUserId(userId);
        if(userEntity1==null)throw new ResourceNotFoundException("User","id",userId);

        if (currentUserId!=userId && currentUserRole.equals("CUSTOMER")) {
            throw new UnAuthorizedPeople("Access Restricted");
        }
        UserEntity userEntity = userRepo.findByUserId(userId);
        List<Bowrrow> bookBorrowings = bowrrowRepo.findAllByUserEntityAndReturnDateIsNull(userEntity);

        List<Book>books=new ArrayList<>();
        for(Bowrrow bowrrow:bookBorrowings)
        {
            books.add(bowrrow.getBook());
        }
        return books.stream().map((todo) -> modelMapper.map(todo, BookDto.class))
                .collect(Collectors.toList());

    }

}
