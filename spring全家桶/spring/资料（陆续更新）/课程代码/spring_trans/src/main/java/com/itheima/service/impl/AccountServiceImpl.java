package com.itheima.service.impl;

import com.itheima.mapper.AccountMapper;
import com.itheima.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void transferAccount(String outAccount, String inAccount, Integer money) {
        accountMapper.incrMoney(inAccount,money);
        int i = 1/0;
        accountMapper.decrMoney(outAccount,money);
    }
}
