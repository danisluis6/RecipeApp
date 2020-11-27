package com.example.lib.localstorage;

/*
 *
 * +--------------------------------------------------------------------------
 * |
 * | WARNING: REMOVING THIS COPYRIGHT HEADER IS EXPRESSLY FORBIDDEN
 * |
 * | RECIPE APPLICATION
 * | ========================================
 * | by ENCLAVE, INC.
 * | (c) 2012-2013 ENCLAVEIT.COM - All right reserved
 * | Website: http://www.enclaveit.com [^]
 * | Email : engineering@enclave.vn
 * | ========================================
 * |
 * | WARNING //--------------------------
 * |
 * | Selling the code for this program without prior written consent is expressly
 * |forbidden.
 * | This computer program is protected by copyright law.
 * | Unauthorized reproduction or distribution of this program, or any portion of
 * | if, may result in severe civil and criminal penalties and will be prosecuted
 * |to the maximum extent possible under the law.
 * +--------------------------------------------------------------------------
 */

/**
 * Created by lorence on 27/November/2020.
 */
public class DatabaseInfo {

    static final int DB_VERSION = 1;

    static final String DB_NAME = "recipeapp";

    public static class Tables {
        public static final String RecipeType = "recipe_type";
        public static final String Recipe = "recipe";
    }

    public static class RecipeType {
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static class Recipe {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TYPE = "type";
        public static final String PICTURE = "picture";
        public static final String INGREDIENTS = "ingredients";
        public static final String STEPS = "steps";
    }

}
