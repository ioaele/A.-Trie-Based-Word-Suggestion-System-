public class TrieNode2 {


    public Element2[] children; //26*element(1B)
    boolean isEndOfWord; //2B
  public int importance;  //4B

  
    public TrieNode2() {
        children = new Element2[26]; 
        isEndOfWord = false;
        importance=0;
    }
    
    public Element2 search2(char ch) {


    Element2 element;


int index = Character.toLowerCase(ch)-'a'; 


if (this == null) {
return null; 
}
element = this.children[index]; 

return element;
}
/*    public void insert(char key) {
    element2=new Element2(key,this);
    
    }*/
}
