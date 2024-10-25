package com.NJU.SWI.LeeBBS.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDaoM1Impl implements AlphaDao{

    @Override
    public String select() {
        return "M1 Impl of AlphaDao";
    }
}
