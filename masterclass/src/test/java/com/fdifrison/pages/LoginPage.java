package com.fdifrison.pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;

public class LoginPage {

  public LoginPage performLogin(String username, String password) {
    $("button.ui").click();
    $("#kc-login").should(Condition.appear);
    $("#username").val(username);
    $("#password").val(password);
    $("#kc-login").click();
    return this;
  }
}
