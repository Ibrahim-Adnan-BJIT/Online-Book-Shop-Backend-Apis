package com.ibrahim.onlinebookshop.service;

import com.ibrahim.onlinebookshop.dto.ReserveDto;
import com.ibrahim.onlinebookshop.exceptions.AlreadyOwned;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.UnAuthorizedPeople;

public interface ReserveService {

    ReserveDto createReserve(int bookId) throws AlreadyOwned, InvalidBookId;
    ReserveDto cancelReserve(int bookId) throws InvalidBookId, UnAuthorizedPeople;
}
