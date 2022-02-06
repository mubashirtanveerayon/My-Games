class Food{
 PVector location;
 int unit; 
 Food(int u){
   unit = u;
   int rx = int(random((int)width/unit));
   int ry = int(random((int)height/unit));
   location = new PVector(rx,ry).mult(unit); 
 }
 
 void show(){
   rect(location.x,location.y,unit,unit);
 }
 
 void generate(){
   int rx = int(random((int)width/unit));
   int ry = int(random((int)height/unit));
   location = new PVector(rx,ry).mult(unit); 
 }

}
