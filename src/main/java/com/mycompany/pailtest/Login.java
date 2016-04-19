/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pailtest;

/**
 *
 * @author Saul
 */
public class Login 
{
    public String userName;
    public long loginUnixTime;
    public Login(String _user, long _login) 
    {
        userName = _user;
        loginUnixTime = _login;
    }
}
