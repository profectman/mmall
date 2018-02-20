package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by zhengquan on 2017/5/29.
 */

public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(String categoryName, Integer categoryId);

    ServerResponse<List<Category>> getCategoryChildInfoByCategoryId(Integer categoryId);

    ServerResponse<List<Integer>> getCateforyAndDescendantIdByCategoryId(Integer categoryId);

}
