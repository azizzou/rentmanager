package com.epf.rentmanager.dao;

import java.sql.SQLException;

public class DaoException extends Exception {
    public  DaoException (SQLException e){
        super(e);
    }
    }


