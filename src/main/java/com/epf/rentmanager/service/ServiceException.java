package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;

public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message , DaoException e ) {
        super(message, e);
    }
    public ServiceException( DaoException e ) {
        super( e);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
