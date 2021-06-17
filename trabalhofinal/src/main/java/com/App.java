package com;

import java.util.Optional;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Optional<String> opt = Optional.empty();
        opt.ifPresentOrElse(
            (string)->System.out.println("tem"), 
            ()->System.out.println("não tem")
        );
    }
}
