package com.scm.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.Objects;

@Component
public class SessionHelper {
        public static void removeMessage(){
            // removing message from session
            try{
                System.out.println("Removing message from session..");
                @SuppressWarnings("null")
                HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
                session.removeAttribute("message");
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            
        }
}
