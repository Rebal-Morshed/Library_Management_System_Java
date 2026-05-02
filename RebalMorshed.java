
import java.util.Scanner; 
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class RebalMorshed {
    
    
public static abstract class Item{
    protected int id;
    protected String title;
    protected int year;
    protected String specialization;
    protected boolean available;
    protected LocalDate borrowDate;   
    protected LocalDate returnDate;
 
 //كونستراكتر
    public Item(int id, String title, int year, String sp){
        this.id = id;
        this.title = title;
        this.year = year;
        this.specialization = sp;
        //قيم افتراضية
        this.available = true; 
        this.borrowDate = null;
        this.returnDate = null;
       }
 
 //Getters
    public int getId(){return id;}
    public String getTitle(){return title;}
    public int getYear(){return year;}
    public String getSpecialization(){return specialization;}
    public boolean getAvailable(){return available;}
    public LocalDate getBorrowDate(){return borrowDate;}
    public LocalDate getReturnDate(){return returnDate;}
    public abstract String getType(); // مجرد حسب نوع العنصر

//Setters
    public void setAvailable(boolean available){this.available = available;}
    public void setBorrowDate(LocalDate date){ this.borrowDate = date; }
    public void setReturnDate(LocalDate date){this.returnDate = date;}   

 // تابع عرض المعلومات ,له تكملة في الأبناء
 public void displayInfo(){
   System.out.println("ID : " + id);
   System.out.println("Title : " + getTitle());
   System.out.println("Year : " + getYear());
   System.out.println("Specialization : " + getSpecialization());
   System.out.println("Available : " + (available ? "yes" : "NO" ));
   }
}


public static class Project extends Item {
  private String academicYear;
  private ArrayList<String> team;
  
  //كونستراكتر
  public Project (int id, String title, int year, String sp, String academicYear, ArrayList<String> team)
  {
   super(id,title,year,sp);
   this.academicYear = academicYear;
   this.team = team;
  }
  
  //getters 
  public String getAcademicYear(){return academicYear;}
  public ArrayList<String> getTeam(){return team;}
  @Override 
  public String getType(){return "(Project)" ;}
  
 //تابع لعرض معلومات ال بروجكت
  @Override 
  public void displayInfo(){
   super.displayInfo();
    System.out.println("Academic Year : " + getAcademicYear());
    System.out.println("Team : ");
   for(String mb : team) {System.out.println(" - " + mb);}
  }
}
public static class Book extends Item {
  private String author;
  private int pages;
  private String publishingHouse;
  
  //كونستراكتر
  public Book(int id, String title, int year, String sp, String author, int pages, String ph) {
    super(id,title,year,sp);
    this.author = author;
    this.pages = pages;
    this.publishingHouse = ph;
  }
  
  // getters
  public String getAuthor(){return author;}
  public int getPages(){return pages;}
  public String getPublishingHouse(){return publishingHouse;}
  @Override 
     public String getType(){return "(Book)";}
  
  // التابع الكامل لعرض معلومات الكتاب
  @Override 
     public void displayInfo(){
   super.displayInfo();
   System.out.println("Author : " + getAuthor());
   System.out.println("Pages : " + getPages());
   System.out.println("Publishing House : " + getPublishingHouse());
   System.out.println(getType());
  }  
}

public static class Member {
 private int id;
 private String name;
 private ArrayList <Item> borrowedItems;
 private ArrayList <LocalDate> borrowDates;
 private ArrayList <LocalDate> returnDates;
 
//كونستراكتر
 public Member(int id, String name)
 {
  this.id = id;
  this.name = name;
  //تهيئة القوائم
  this.borrowedItems = new ArrayList<Item>();
  this.borrowDates = new ArrayList<LocalDate>();
  this.returnDates = new ArrayList<LocalDate>();
 }
 
 //getters
 public int getId(){return id;}
 public String getName(){return name;}
 public ArrayList<Item> getBorrowedItems(){return borrowedItems;}
 public ArrayList<LocalDate> getBorrowDates(){return borrowDates;}
 public ArrayList<LocalDate> getReturnDates(){return returnDates;}
 
 public void borrowItem(Item item) {
     //شرط لمنع استعارة اكثر من 3 عناصر
  if(borrowedItems.size() >= 3)
   {
   System.out.println("You have reached the maximum number of borrowing !");
   return;
   }
  if (item.getAvailable()) 
  {
   borrowedItems.add(item);
   
   borrowDates.add(LocalDate.now());
   returnDates.add(null);
   
   item.setBorrowDate(LocalDate.now());
   
   item.setAvailable(false);
   System.out.println("Borrowed : " + item.getTitle()); 
  }
  else
  {
   System.out.println("Unavailable !");  
  }
 }
 public void returnItem(Item item)
 {
     //حتى نتحقق من الاعارة :
   for(int i=0; i<borrowedItems.size(); i++){
   if (borrowedItems.get(i) == item)
   {
    item.setAvailable(true);
    
    returnDates.set(i,LocalDate.now()); 
    
    item.setReturnDate(LocalDate.now());
    
    System.out.println("Returned : " + item.getTitle());
    }
   else
   {
    System.out.println("This item is not borrowed from this member !") ; 
    }
  }
 }
 
 // تابع  لعرض معلومات العضو
 public void displayInfo()
 {
  System.out.println("Member ID : " + id);
  System.out.println("Name : " + name);
  System.out.println("Borrowed Items : ");
  if (borrowedItems.isEmpty())
  {
    System.out.println("Nothing !");
  }
  else
  {
   for (Item item : borrowedItems) 
   {
    System.out.println(" - " + item.getTitle() + item.getType());
   }
  }
 }
 
 // تابع لعرض سجل الاعارات
 public void displayBorrowHistory(){
  System.out.println(name + "'s borrowing history : ");  
  for(int i=0 ; i < borrowedItems.size() ; i++){
    Item item = borrowedItems.get(i);
    LocalDate bDate = borrowDates.get(i);
    LocalDate rDate = returnDates.get(i);
    System.out.println("Title : " + item.getTitle());
    System.out.println("Borrow date : " + bDate);
    if(rDate != null)
    {
     System.out.println("Return date : " + rDate);
    }
    else
    {
     System.out.println("The item has not returned yet !");
    }
    System.out.println("---------------------------------------");
  }
 }
}

public static class Library {
 private ArrayList<Item> items = new ArrayList<Item>();
 private ArrayList<Member> members = new ArrayList<Member>();
 
 //getters
 public ArrayList<Item> getItems(){return items;}
 public ArrayList<Member> getMembers(){return members;}
 
// تابع لاضافة عنصؤ
 public void addItem(Item item) 
 {
  items.add(item);
  System.out.println("Added : " + item.getTitle());
 }
 
// تابع لاضافة عضو
 public void addMember(Member member)
 {
  members.add(member);
  System.out.println("Added : " + member.getName());
  }

 //تابع لعرض كل الكتب
 public void showBooks()
 {
  boolean f = false;
  for(Item item : items){
   if(item.getType().equals("(Book)")){
    System.out.println("----------------------------");
    item.displayInfo();
    f = true; 
   }    
  }
  if(f == false) {System.out.println("Empty !");}
 }
 
 //تابع لعرض الكتب المعارة
 public void showBorrowedBooks()
 {
  boolean f = false;
  for(Item item : items){
   if(item.getType().equals("(Book)") && item.getAvailable() == false)
   {
    System.out.println("----------------------------");
    item.displayInfo();
    f = true; 
   }    
  }
  if(f == false) {System.out.println("Empty !");}
 }
 
 //تابع لعرض  كل المشاريع
  public void showProjects()
 {
  boolean f = false;
  for(Item item : items){
   if(item.getType().equals("(Project)")) {
    f = true;
    System.out.println("----------------------------");
    item.displayInfo();
   }    
  }
  if(f == false) {System.out.println("Empty !");}
 }  
  
  //تابع لعرض المشاريع حسب السنة الدراسية
  public void showYearProjects(String ACyear)
 {
  boolean f = false;
  for(Item item : items){
   if(item.getType().equals("(Project)")) {
    Project project = (Project)item;
    /*قمنا بعمل كاستينج للعنصر و جعلنا نوعه مشروع حتى نستخدم خواص المشروع*/
    
    if(project.getAcademicYear().equals(ACyear)) {   
     System.out.println("----------------------------");
     project.displayInfo();
     f = true; 
    }
   }    
  }
  if(f == false) {System.out.println("Empty !");}
 }
  
  //تابع لعرض المشاريع المتاحة جسب الاختصاص
   public void showSPAvailableProjects(String specialization)
 {
  boolean f = false;
  for(Item item : items){
   if(item.getType().equals("(Project)") && item.getSpecialization().equals(specialization)
   && item.getAvailable() )
   {
    Project project = (Project)item;
     /*قمنا بعمل كاستينج للعنصر و جعلنا نوعه مشروع حتى نستخدم خواص المشروع*/
     
     System.out.println("----------------------------");
     project.displayInfo();
     f = true; 
    
   }    
  }
  if(f == false) {System.out.println("Empty !");}
 } 
   
   //تابع لعرض كل العناصر
 public void showItems()
 {
  if (items.isEmpty()) {System.out.println("Empty !"); return;}
  for(Item item : items) {
   System.out.println("----------------------------");
   item.displayInfo();  
  }
 }
 
 //تابع لعرض العناصر المتأخرة
 public void showLateItems(){
  boolean f = false;
  LocalDate today = LocalDate.now();
  for(Member member : members){
   ArrayList<Item> items = member.getBorrowedItems();
   ArrayList<LocalDate> borrowDates = member.getBorrowDates();
   ArrayList<LocalDate> returnDates = member.getReturnDates();
   
   for(int i=0 ; i<items.size() ; i++){
    if(returnDates.get(i) == null){      
     long days = ChronoUnit.DAYS.between(borrowDates.get(i) , today);//لحساب فرق الوقت بالأيام
     if(days > 7){
      System.out.println(member.getName() + " has borrowed : " + items.get(i).getTitle()
       + " for " + days + " days ago, and hasn't returned it yet ! ");
       f = true;
     }
    }
   }
  } 
  if(f == false){ System.out.println("There is no late items !"); }
 }
 
//تابع لعرض كل الاعضاء
 public void showMembers()
 {
  if(members.isEmpty()) {System.out.println("Empty !"); return;}
  for(Member member : members) {
   System.out.println("----------------------------");
   member.displayInfo();  
  }
 }
 
 //تابع لعرض الاعضاء الذين استعاروا كتاب ذكاء اصطناعي
 public void showAIMembers()
 {
  boolean f = false;
  for(Member member : members) {
   for(Item item : member.getBorrowedItems()) {
    if(item.getType().equals("(Book)") && item.getSpecialization().equals("AI")) {   
     System.out.println("----------------------------");
     member.displayInfo();  
     f = true;
    }
   }
  }
  if (f == false) {System.out.println("Not found !");}
 }
 
 //تابع لعرض سجلات الاعارة لعضو
 public void showMemberHistory(int memberID){
   Member member = idFindMember(memberID);
   if(member == null){System.out.println("Member not found!"); return;}
   member.displayBorrowHistory();
  }
 
 //تابع لعرض الاعضاء الذين استعارو خلال فترة معينة
 public void showMembersByPeriod(LocalDate from, LocalDate to){
  boolean f = false;
  for(Member member : members){
   ArrayList<LocalDate> borrowDates = member.getBorrowDates();
   for(LocalDate date : borrowDates){
    if(date.isEqual(from) || date.isAfter(from) && date.isBefore(to)){
     System.out.println(member.getName() + " borrowed during this period.");
     f = true;
     break;
    }
   }
  }
  if(f == false){System.out.println("There is no members have borrowed during this period !");}
 }
  
 //تابع البحث عن عنصر حسب العنوان
 public void ttFindItem(String title){
  boolean f = false;
  for(Item item : items){
   if(item.getTitle().equals(title) ) {
    System.out.println("----------------------------");
    item.displayInfo();
    f = true;
   }
  }
  if(f == false){System.out.println("Not found !");}
 }
 
 //تابع البحث عن عنصر حسب الاختصاص
 public void spFindItem(String specialization){
   boolean f = false;
  for(Item item : items){
   if(item.getSpecialization().equals(specialization)) {
    System.out.println("----------------------------");
    item.displayInfo();
    f = true;
   }
  }
  if(f == false){System.out.println("Not found !");}
 }
 
  //تابع البحث عن عنصر حسب المعرف
 //جعلنا نوع الميثود آيتم لكي نرجع العنصر حيث استخدمناه في ميثودات أخرى
 public Item idFindItem(int id){
  for(Item item : items){
   if(item.getId() == id){
    System.out.println("----------------------------");
    item.displayInfo();
    return item;
   }
  }
   System.out.println("Not found !");
   return null;
 }
 
 //تابع البحث عن عضو حسب المعرف
 //جعلنا نوع الميثود ميمبر لكي نرجع العضو حيث استخدمناه في ميثودات اخرى
 public Member idFindMember(int id){
  for(Member member : members){
   if(member.getId() == id){
    System.out.println("----------------------------");
    member.displayInfo();
    return member;
   }
  }
  System.out.println("Not found !"); 
  return null;
 }
 
 //تابع الاعارة
 public void borrowItem(int itemId, int memberId){
  Item item = idFindItem(itemId);
  Member member = idFindMember(memberId);
  if(item == null){System.out.println("Item not found !"); return;}
  if(member == null){System.out.println("Member not found !"); return;}
  if( ! item.getAvailable()) {System.out.println("Unavailable !"); return;}
  member.borrowItem(item);
  System.out.println("Done.");
  }
 
 //تابع الارجاع
 public void returnItem(int itemId, int memberId){
  Item item = idFindItem(itemId);
  Member member = idFindMember(memberId);
  if(item == null){System.out.println("Item not found !"); return;}
  if(member == null){System.out.println("Member not found !"); return;}
  if(item.getAvailable()) {System.out.println("Not borrowed !"); return;}
  member.returnItem(item);
  System.out.println("Done.");
  }
}

public static void main(String[] args){
  Scanner sc = new Scanner(System.in);
  Library library = new Library();
  
  //قمنا بانشاء اوبجكتات جاهزة  بوقت اعارة متاخر لتطبيق ميثود التأخير
  Member memb1 = new Member(1, "Omar");
  Book   bk    = new Book(1,"Open AI", 2020, "AI", "R.MD", 150, "SYR. Publishing House");
  
  library.addMember(memb1);
  library.addItem(bk);
  
  memb1.borrowItem(bk);
  memb1.getBorrowDates().set(0,LocalDate.of(2025,01,01));
  
  
  while(true)
  {
    System.out.println("\n======Library System======\n");
    System.out.println("1. Show.");
    System.out.println("2. Find.");
    System.out.println("3. Operations.");
    System.out.println("0. Exit.");
    System.out.print("Your Choice is : ");
    
    int choice = sc.nextInt();
    sc.nextLine();
   if(choice == 0){System.out.println("Good-Bye!"); break;}
    switch (choice)
    {
      case 1 : 
      while(true)
      {
       System.out.println("\n======Show Menu======\n");
       System.out.println("1. Show all items.");
       System.out.println("2. Show books.");
       System.out.println("3. Show projects.");
       System.out.println("4. Show borrowed books.");
       System.out.println("5. Show project by academic year.");
       System.out.println("6. Show available projects by specializatoin.");
       System.out.println("7. Show members who have borrowed (AI) books.");
       System.out.println("8. Show members who have borrowed, during a period of time.");
       System.out.println("9. Show borrowing history.");
       System.out.println("10. Show late items.");
       System.out.println("0. Go back.");
       System.out.print("Your Choice is : ");
       int sChoice = sc.nextInt();
       sc.nextLine();
       if(sChoice == 0){break;}
       switch (sChoice)
       {
         case 1 :     library.showItems();              break;
         case 2 :     library.showBooks();              break;
         case 3 :     library.showProjects();           break;
         case 4 :     library.showBorrowedBooks();      break;
         
         case 5 : 
         System.out.print("Enter the academic year : ");
         String year = sc.nextLine();
         library.showYearProjects(year);                break;
         
         case 6 : 
         System.out.print("Enter the specialization : ");
         String sp = sc.nextLine();
         library.showSPAvailableProjects(sp);           break;
         
         case 7 :      library.showAIMembers();         break;
         
         case 8 : 
         System.out.println("Enter the date : ");
         System.out.print("FROM (yyyy-mm-dd) : ");
         String from = sc.nextLine();
         System.out.print("TO (yyyy-mm-dd) : ");
         String to = sc.nextLine();
         
         LocalDate frooom = LocalDate.parse(from);
         LocalDate tooo = LocalDate.parse(to);
         /* استعملنا بارس  لنحول التاريخ من نص الى رقم */
         
         library.showMembersByPeriod(frooom, tooo);   break;
         
         case 9 : 
         System.out.print("Enter the member ID : ");
         int mId = sc.nextInt();
         sc.nextLine();
         library.showMemberHistory(mId);             break;
         
         case 10 :     library.showLateItems();      break;
         
         default : System.out.print("Invalid choice !");
        }
      }
      break;
      
      case 2 :
      while(true)
      {
       System.out.println("\n======Find Menu======\n");
       System.out.println("1. Find an item by title.");
       System.out.println("2. Find an item by specialization."); 
       System.out.println("3. Find an item by ID.");
       System.out.println("4. Find a member by ID.");
       System.out.println("0. Go back.");
       System.out.print("Your choice is : ");
       int fChoice = sc.nextInt();
       sc.nextLine();
       if(fChoice == 0) {break; }
       switch(fChoice)
       {
        case 1 :
        System.out.print("Enter the title : ");
        String title = sc.nextLine();
        library.ttFindItem(title);
        break;
        
        case 2 :
        System.out.print("Enter the specialization : ");
        String sp = sc.nextLine();
        library.spFindItem(sp);
        break;
        
        case 3 :
        System.out.print("Enter the ID : ");
        int itemID = sc.nextInt();
        sc.nextLine();
        library.idFindItem(itemID);
        break;
        
        case 4 :
        System.out.print("Enter the ID : ");
        int memberID = sc.nextInt();
        sc.nextLine();
        library.idFindMember(memberID);
        break;
        
        default : System.out.print("Invalid choice !");
        }
        }
      break;
        
      case 3 : 
      while(true)
      {
       System.out.println("\n====Operations Menu====\n");
       System.out.println("1. Add member.");
       System.out.println("2. Add item."); 
       System.out.println("3. Borrow item.");
       System.out.println("4. Return item.");
       System.out.println("0. Go back.");
       System.out.print("Your Choice is : ");
       
       int oChoice = sc.nextInt();
       sc.nextLine();
       if(oChoice == 0){break;}
       switch(oChoice)
       {
        case 1 :
        System.out.print("Enter the member name : ");
        String name = sc.nextLine();
        System.out.print("Enter the member ID : ");
        int memID = sc.nextInt();
        
        
        Member member = new Member(memID, name);
        library.addMember (member);
        break;
        
        case 2 :
        System.out.println("Choose the type of the item : ");
        System.out.println("1. (Book)");
        System.out.println("2. (Project)");
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the ID : ");
        int itemID = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the title : ");
        String title = sc.nextLine();
        System.out.print("Enter the year of publication : ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the specializatoin : ");
        String sp = sc.nextLine();
        
        if(type == 1)
        {
         System.out.print("Enter the author's name : ");
         String author = sc.nextLine();
         System.out.print("Enter the number of pages : ");
         int pages = sc.nextInt();
         sc.nextLine();
         System.out.print("Enter the name of the publishing house : ");
         String pHouse = sc.nextLine();
         
         Book book = new Book (itemID, title, year, sp, author, pages, pHouse);
         library.addItem(book);
        }
         else if(type == 2)
        {
          System.out.print("Enter the academic year : ");
          String academicYear = sc.nextLine();
          System.out.print("Enter the number of team members : ");
          int team = sc.nextInt();
          sc.nextLine();
          
          ArrayList<String> pTeam = new ArrayList<String>();
          for(int i=1; i <= team ; i++ )
          {
           System.out.print("Enter the name of member ("+ i +") : ");
           String memberName = sc.nextLine();
           pTeam.add(memberName);
          }
           Project project = new Project(itemID, title, year, sp, academicYear, pTeam);
           library.addItem(project);
         }
        else
        {     
         System.out.println("Invalid type !");
         }
        break;
         
        case 3 :
        System.out.print("Enter the item ID : ");
        int bItemID = sc.nextInt();
        System.out.println("Enter the member ID : ");
        int bMemberID = sc.nextInt();
        library.borrowItem(bItemID, bMemberID);
        break;
        
        case 4 :
        System.out.print("Enter the item ID : ");
        int rItemID = sc.nextInt();
        System.out.println("Enter the member ID : ");
   
        int rMemberID = sc.nextInt();
        library.returnItem(rItemID, rMemberID);
        break;
        
        default :
        System.out.println("Invalid choice !");
        break;
      }
    }  
   }
  }
 }  

        
        
    
    
}
