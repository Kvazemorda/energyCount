import java.util.ArrayList;
import java.util.Scanner;

public class rcc {
    public static void main(String[] args) {
        Scanner query = new Scanner(System.in);
        Scanner secondQuery = new Scanner(System.in);
        int coutTest = Integer.valueOf(query.nextLine());

        for(int x = 0; x < coutTest; x++){
            String seconQueryString = secondQuery.nextLine();
            int countList = Integer.valueOf(seconQueryString.split(" ")[0]);
            int countQuery = Integer.valueOf(seconQueryString.split(" ")[1]);
            int allPage = countList;
            ArrayList<Integer> list = new ArrayList<>();
            String parser = "";
            for(int i = 1; i <= countList; i++){
                list.add(i);
            }
            for(int i = 0; i < countQuery; i++){
                parser = secondQuery.nextLine();
                String one = parser.split(" ")[0];
                int pageDelete = Integer.parseInt(parser.split(" ")[1]);
                if(one.equals("?")){
                    int findPage = pageDelete - 1;
                    System.out.println(list.get(findPage));
                }else{if(one.equals("-")){
                    for(int j = 0; j < countList; j++){
                        if (list.get(j) == pageDelete){
                            list.remove(j);
                            for(int z = 0; z < countList; z++){
                                if(list.get(z) == (allPage - pageDelete + 1)){
                                    list.remove(z);
                                    countList--;
                                    countList--;
                                }
                            }
                        }
                    }
                }
                }

            }
        }
    }
}
