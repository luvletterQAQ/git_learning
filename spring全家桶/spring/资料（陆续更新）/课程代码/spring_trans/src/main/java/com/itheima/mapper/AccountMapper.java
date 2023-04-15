package com.itheima.mapper;

public interface AccountMapper {

    //转出钱
    public void decrMoney(String accountName,Integer money);
    //转入钱
    public void incrMoney(String accountName,Integer money);


}
