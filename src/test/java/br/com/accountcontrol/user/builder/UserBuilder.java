package br.com.accountcontrol.user.builder;

import br.com.accountcontrol.user.model.User;

public class UserBuilder {

    public static User USER = User.builder().username("luiz.reis").name("luiz").password("123").build();
}
