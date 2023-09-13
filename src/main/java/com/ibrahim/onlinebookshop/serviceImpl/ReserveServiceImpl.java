package com.ibrahim.onlinebookshop.serviceImpl;

import com.ibrahim.onlinebookshop.dto.ReserveDto;
import com.ibrahim.onlinebookshop.exceptions.AlreadyOwned;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.UnAuthorizedPeople;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Reserve;
import com.ibrahim.onlinebookshop.model.UserEntity;
import com.ibrahim.onlinebookshop.repository.BookRepo;
import com.ibrahim.onlinebookshop.repository.BowrrowRepo;
import com.ibrahim.onlinebookshop.repository.ReserveRepo;
import com.ibrahim.onlinebookshop.repository.UserRepo;
import com.ibrahim.onlinebookshop.service.ReserveService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class ReserveServiceImpl implements ReserveService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BowrrowRepo bowrrowRepo;

    @Autowired
    private ReserveRepo reserveRepo;

    @Override
    public ReserveDto createReserve(int bookId) throws AlreadyOwned, InvalidBookId {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user=userRepo.findByEmail(authentication.getName());
        int userId=user.get().getUserId();
        UserEntity user1=userRepo.findByUserId(userId);
        Book book=bookRepo.findByBookId(bookId);
        if(book==null) throw new InvalidBookId("Book id is Not Valid");
        if(book.getIsAvailable().equals("Available")) throw new AlreadyOwned("This book is Available so You can Bowrrow it");
        Reserve reserve=new Reserve();
        reserve.setReserveDate(LocalDate.now());
        reserve.setBook(book);
        reserve.setUserEntity(user1);
        reserve.setReservationStatus("Pending");
       reserveRepo.save(reserve);
       return modelMapper.map(reserve,ReserveDto.class);
    }

    @Override
    public ReserveDto cancelReserve(int bookId) throws UnAuthorizedPeople,InvalidBookId {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user=userRepo.findByEmail(authentication.getName());
        int userId=user.get().getUserId();
        UserEntity user1=userRepo.findByUserId(userId);
        Book book=bookRepo.findByBookId(bookId);
        if(book==null) throw new InvalidBookId("Book id is Not Valid");
        Reserve reserve=reserveRepo.findByBook(book);

        if(reserve.getUserEntity()!=user1)throw new UnAuthorizedPeople("You are not authorized owner");

        reserve.setReservationStatus("Canceled");
        reserveRepo.save(reserve);
        return modelMapper.map(reserve,ReserveDto.class);

    }
}
