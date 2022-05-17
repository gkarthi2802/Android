package Pages;

public class AllPages {
    protected HomePage homepage;

    protected HomePage getHomePage( ){
        return new HomePage();
    }

    public AllPages() {
       homepage = getHomePage();
    }


}
