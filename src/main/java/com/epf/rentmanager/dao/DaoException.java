package com.epf.rentmanager.dao;

import java.sql.SQLException;

public class DaoException extends Exception {
    public  DaoException (SQLException e){
        super(e);
    }
    public  DaoException (String d,SQLException e){
        super(d , e);
    }
    public  DaoException (String d){
        super(d );
    }
    }


