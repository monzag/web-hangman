package com.codecool.webhangman.service.sessioninterpreter;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SessionInterpreterService {

    public RequestInterpreter create(HttpServletRequest request) {
        return new RequestInterpreter(request);
    }
}
