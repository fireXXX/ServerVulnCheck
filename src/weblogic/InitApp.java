package weblogic;


import java.rmi.Remote;

public abstract interface InitApp extends Remote
{
  public abstract String runCmd(String paramString);
}