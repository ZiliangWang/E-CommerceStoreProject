package com.cy.store.service;

import com.cy.store.vo.CartVO;

import java.util.List;

public interface ICartService {

    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    List<CartVO> getCartVOByUid(Integer uid);

    Integer updateCartNum(Integer cid, Integer amount, Integer uid, String username);

//    Integer addOneItem(Integer cid, Integer uid, String username);

    void deleteCartItem(Integer cid, Integer uid);

    List<CartVO> getCartVOByCids(Integer uid, Integer[] cids);
}
