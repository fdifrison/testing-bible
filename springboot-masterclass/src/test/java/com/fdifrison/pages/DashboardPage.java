package com.fdifrison.pages;

import com.codeborne.selenide.Selenide;

public class DashboardPage {
  public DashboardPage open() {
    Selenide.open("/");
    return this;
  }
}
