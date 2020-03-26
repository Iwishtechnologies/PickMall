package tech.iwish.pickmall.OkhttpConnection;

import java.util.ArrayList;
import java.util.List;

import tech.iwish.pickmall.other.FlashsalemainList;
import tech.iwish.pickmall.other.FriendSaleList;
import tech.iwish.pickmall.other.ItemList;
import tech.iwish.pickmall.other.SilderLists;

public class ProductListF {


    public static List item_facklist(){
        List<ItemList> itemListsfask = new ArrayList<>();
        itemListsfask.add(new ItemList("null","",""));
        itemListsfask.add(new ItemList("","",""));
        itemListsfask.add(new ItemList("","",""));
        itemListsfask.add(new ItemList("","",""));
        itemListsfask.add(new ItemList("","",""));
        itemListsfask.add(new ItemList("","",""));
        return itemListsfask;
    }

    public static List silder_list_fack(){
        List<SilderLists> silderListsList = new ArrayList<>();
        silderListsList.add(new SilderLists("null","","",""));
        silderListsList.add(new SilderLists("null","","",""));
        silderListsList.add(new SilderLists("null","","",""));
        return silderListsList;
    }

    public static List flash_sale_list_fack(){
        List<FlashsalemainList> flashsalemainLists = new ArrayList<>();
        flashsalemainLists.add(new FlashsalemainList("null","","","","","","","","","","",""));
        flashsalemainLists.add(new FlashsalemainList("null","","","","","","","","","","",""));
        flashsalemainLists.add(new FlashsalemainList("null","","","","","","","","","","",""));
        flashsalemainLists.add(new FlashsalemainList("null","","","","","","","","","","",""));
        flashsalemainLists.add(new FlashsalemainList("null","","","","","","","","","","",""));
        flashsalemainLists.add(new FlashsalemainList("null","","","","","","","","","","",""));
        return flashsalemainLists;
    }


    public static List friend_deal_list_fack(){
        List<FriendSaleList> friendSaleLists = new ArrayList<>();
        friendSaleLists.add(new FriendSaleList("null" , "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null" , "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null" , "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null" , "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null" , "", "", "", "", "", "", "", "", "", "", ""));
        friendSaleLists.add(new FriendSaleList("null" , "", "", "", "", "", "", "", "", "", "", ""));
        return friendSaleLists;
    }
}
























