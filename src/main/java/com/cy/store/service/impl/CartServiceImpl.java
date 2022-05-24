package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;


    @Override
    public void addToCart(Integer uid, Integer pid,
                          Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if(result == null) {

            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setPrice(productMapper.findById(pid).getPrice());
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());

            Integer rows = cartMapper.insert(cart);
            if(rows != 1) {
                throw new InsertException("Adding new item to cart failed!");
            }
        } else {
            Integer newAmount = result.getNum() + amount;
            Integer rows = cartMapper.updateNumberByCid(result.getCid(), newAmount, username, new Date());
            if(rows != 1) {
                throw new UpdateException("Changing number of item failed!");
            }
        }
    }

    @Override
    public List<CartVO> getCartVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

//    @Override
//    public Integer addOneItem(Integer cid, Integer uid, String username) {
//        Cart result = cartMapper.findByCid(cid);
//
//        if(result == null) {
//            throw new CartNotFoundException("Item data not exist!");
//        }
//
//        if(result.getUid()!=uid) {
//            throw new AccessDeniedException("Cart access denied!");
//        }
//
//        Integer rows = cartMapper.updateNumberByCid(cid, result.getNum()+1, username, new Date());
//
//        if(rows != 1) {
//            throw new UpdateException("Update cart number failed!");
//        }
//        return null;
//    }

    @Override
    public Integer updateCartNum(Integer cid, Integer amount, Integer uid, String username) {
        checkEligibility(cid, uid);

        Integer rows = cartMapper.updateNumberByCid(cid, amount, username, new Date());
        if(rows != 1) {
            throw new UpdateException("Update cart number failed!");
        }
        return amount;
    }

    @Override
    public void deleteCartItem(Integer cid, Integer uid) {
        checkEligibility(cid, uid);
        Integer rows = cartMapper.deleteByCid(cid);
        if(rows != 1) {
            throw new UpdateException("Update cart number failed!");
        }
    }

    @Override
    public List<CartVO> getCartVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);

        Iterator<CartVO> it = list.iterator();
        while(it.hasNext()) {
            CartVO cartVO = it.next();
            if(!cartVO.getUid().equals(uid)){
                list.remove(cartVO);
            }
        }
        return list;
    }

    private void checkEligibility(Integer cid, Integer uid) {
        Cart result = cartMapper.findByCid(cid);

        if(result == null) {
            throw new CartNotFoundException("Item data not exist!");
        }

        if(result.getUid()!=uid) {
            throw new AccessDeniedException("Cart access denied!");
        }
    }

}
