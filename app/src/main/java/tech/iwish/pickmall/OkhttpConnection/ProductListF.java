package tech.iwish.pickmall.OkhttpConnection;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.other.FIltersideList;
import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.other.FriendSaleList;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.other.SilderLists;

public class ProductListF {


    public static List item_fakelist() {
        List<ItemList> itemListsfask = new ArrayList<>();
        itemListsfask.add(new ItemList("null", "", "","",""));
        itemListsfask.add(new ItemList("", "", "","",""));
        itemListsfask.add(new ItemList("", "", "","",""));
        itemListsfask.add(new ItemList("", "", "","",""));
        itemListsfask.add(new ItemList("", "", "","",""));
        itemListsfask.add(new ItemList("", "", "","",""));
        return itemListsfask;
    }

    public static List silder_list_fack() {
        List<SilderLists> silderListsList = new ArrayList<>();
        silderListsList.add(new SilderLists("null", "", "", ""));
        silderListsList.add(new SilderLists("null", "", "", ""));
        silderListsList.add(new SilderLists("null", "", "", ""));
        return silderListsList;
    }

    public static List flash_sale_list_fake() {
        List<FlashsalemainList> flashsalemainLists = new ArrayList<>();
        flashsalemainLists.add(new FlashsalemainList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        flashsalemainLists.add(new FlashsalemainList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        flashsalemainLists.add(new FlashsalemainList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        flashsalemainLists.add(new FlashsalemainList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        flashsalemainLists.add(new FlashsalemainList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        flashsalemainLists.add(new FlashsalemainList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        return flashsalemainLists;
    }


    public static List friend_deal_list_fake() {
        List<FriendSaleList> friendSaleLists = new ArrayList<>();
        friendSaleLists.add(new FriendSaleList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        return friendSaleLists;
    }


    public static List FlashSalefake() {

        List<FlashsalemainList> flashsalefakelist = new ArrayList<>();

        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));
        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));
        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));
        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));
        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));
        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));
        flashsalefakelist.add(new FlashsalemainList("null","","","","","","","","","","","","","",""));

        return flashsalefakelist;
    }


    public static List FilterSideList(){

        List<FIltersideList> fIltersideLists = new ArrayList<>();

        fIltersideLists.add(new FIltersideList("Size"));
        fIltersideLists.add(new FIltersideList("Color"));
        fIltersideLists.add(new FIltersideList("Brand"));
        fIltersideLists.add(new FIltersideList("Categories"));
        return fIltersideLists;
    }



}
























