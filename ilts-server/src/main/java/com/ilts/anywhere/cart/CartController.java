/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: CartController.java
 */
package com.ilts.anywhere.cart;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilts.anywhere.authentication.AuthenticationService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authService;

    @RequestMapping("/cart/addOrder")
    public @ResponseBody
    Object addOrder(@RequestBody String jsonRequest, HttpServletResponse response) {
        this.cartService.insertOrder(jsonRequest);

//		return loginResponse;
        return new Object() {
            public Boolean status = true;
        };
    }

    @RequestMapping("/cart/getOrders")
    public @ResponseBody
    ResponseEntity<List<Order>> getOrders(@RequestParam(value = "token", required = true) String token,
            HttpServletResponse response) {
        if (!this.authService.validSession(token)) {
            return new ResponseEntity<List<Order>>(HttpStatus.UNAUTHORIZED);
        } else {
            String userId = this.authService.getUserId(token);
            List<Order> orders = this.cartService.getOrders(userId);

            return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
        }

//        Response ret = this.cartService.getOrders(token);
//        return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
////        return new ResponseEntity<Response>(ret, HttpStatus.OK);
    }

    @RequestMapping("/cart/getAllOrders")
    public @ResponseBody
    ResponseEntity<List<Order>> getAllOrders(@RequestParam(value = "token", required = true) String token,
            HttpServletResponse response) {
        if (!this.authService.validSession(token)) {
            return new ResponseEntity<List<Order>>(HttpStatus.UNAUTHORIZED);
        } else {
            if (!this.authService.isAdmin(token)) {
                return new ResponseEntity<List<Order>>(HttpStatus.FORBIDDEN);
            } else {
                List<Order> orders = this.cartService.getAllOrders();

                return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
            }
        }

//        Response ret = this.cartService.getOrders(token);
//        return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
////        return new ResponseEntity<Response>(ret, HttpStatus.OK);
    }

    @RequestMapping("/cart/getOrder")
    public @ResponseBody
    Object getOrder(@RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "token", required = true) String token,
            HttpServletResponse response) {
        return new Object() {
            public Boolean status = true;
        };
    }

    @RequestMapping(value = "/cart/removeOrder/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object removeOrder(@PathVariable("id") String id, HttpServletResponse response) {
        this.cartService.deleteOrder(id);

        return new Object() {
            public Boolean status = true;
        };
    }
}
