package com.codecool.webhangman.controller;

import com.codecool.webhangman.service.sessioninterpreter.RequestInterpreter;
import com.codecool.webhangman.service.sessioninterpreter.SessionInterpreterService;
import com.codecool.webhangman.service.permissionsmanagementservice.AccessGuardian;
import com.codecool.webhangman.service.permissionsmanagementservice.LoggedInAccessPeeper;
import com.codecool.webhangman.service.permissionsmanagementservice.UnLoggedAccessPeeper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Scope("prototype")
public class SessionManager implements HandlerInterceptor {
    private AccessGuardian accessGuardian;
    private SessionInterpreterService sessionInterpreterService;

    public SessionManager(AccessGuardian accessGuardian,
                          SessionInterpreterService sessionInterpreterService) {
        this.accessGuardian = accessGuardian;
        this.sessionInterpreterService = sessionInterpreterService;
    }

    //region setters and getters
    public AccessGuardian getAccessGuardian( ) {
        return accessGuardian;
    }

    public void setAccessGuardian(AccessGuardian accessGuardian) {
        this.accessGuardian = accessGuardian;
    }
    //endregion

    // This method is called before the controller
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        RequestInterpreter requestInterpreter = this.sessionInterpreterService.create(request);
        boolean isUserLoggedIn = requestInterpreter.isUserLoggedIn();
        String currentPath = request.getServletPath();

        LoggedInAccessPeeper loggedInAccessPeeper = this.accessGuardian.getLoggedInAccessPeeper();
        UnLoggedAccessPeeper unLoggedAccessPeeper = this.accessGuardian.getUnLoggedAccessPeeper();

        if (isUserLoggedIn && !loggedInAccessPeeper.contains(currentPath)) {
            response.sendRedirect(loggedInAccessPeeper.getDefaultPath());
            return false;

        } else if (!isUserLoggedIn && !unLoggedAccessPeeper.contains(currentPath)) {
            response.sendRedirect(unLoggedAccessPeeper.getDefaultPath());
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}