package com.codecool.webhangman.service.permissionsmanagementservice;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class AccessGuardian {
    private LoggedInAccessPeeper loggedInAccessPeeper;
    private UnLoggedAccessPeeper unLoggedAccessPeeper;

    public AccessGuardian(LoggedInAccessPeeper loggedInAccessPeeper,
                          UnLoggedAccessPeeper unLoggedAccessPeeper) {
        this.loggedInAccessPeeper = loggedInAccessPeeper;
        this.unLoggedAccessPeeper = unLoggedAccessPeeper;
    }

    public LoggedInAccessPeeper getLoggedInAccessPeeper( ) {
        return loggedInAccessPeeper;
    }

    public UnLoggedAccessPeeper getUnLoggedAccessPeeper( ) {
        return unLoggedAccessPeeper;
    }

    public void setLoggedInAccessPeeper(LoggedInAccessPeeper loggedInAccessPeeper) {
        this.loggedInAccessPeeper = loggedInAccessPeeper;
    }

    public void setUnLoggedAccessPeeper(UnLoggedAccessPeeper unLoggedAccessPeeper) {
        this.unLoggedAccessPeeper = unLoggedAccessPeeper;
    }
}
