package com.dawid.dao;

import com.dawid.entity.Result;

import java.util.List;

public interface ResultDAO {
    List<Result> getAll();
    void saveOrUpdate(Result result);

}
