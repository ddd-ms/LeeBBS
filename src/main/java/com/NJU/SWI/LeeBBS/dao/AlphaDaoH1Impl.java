package com.NJU.SWI.LeeBBS.dao;

import org.springframework.stereotype.Repository;

@Repository("adh1p")
public class AlphaDaoH1Impl implements AlphaDao{
    @Override
    public String select() {
        return "H1 Impl of AlphaDao";
    }
}
