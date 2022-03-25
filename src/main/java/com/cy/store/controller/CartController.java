package com.cy.store.controller;

import com.cy.store.entity.Cart;
import com.cy.store.service.ICartService;
import com.cy.store.service.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    ICartService cartService;
    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        System.out.println("pid=" + pid);
        System.out.println("amount=" + amount);
        cartService.addToCart(getUidFromSession(session), pid, amount, getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getCartVOByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("{cid}/num/change_num")
    public JsonResult<Integer> changeNumOfCartItem(@PathVariable("cid") Integer cid, Integer amount, HttpSession session) {
        Integer data = cartService.updateCartNum(cid, amount,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult<>(OK, data);
    }

    @RequestMapping("{cid}/delete_item")
    public JsonResult<Void> deleteCartItem(@PathVariable("cid") Integer cid, HttpSession session) {
        cartService.deleteCartItem(cid,getUidFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping({"list"})
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        List<CartVO> data = cartService.getCartVOByCids(getUidFromSession(session), cids);
        return new JsonResult<>(OK, data);
    }
}
