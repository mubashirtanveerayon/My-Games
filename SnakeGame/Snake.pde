PVector right = new PVector(1, 0);
PVector left = new PVector(-1, 0);
PVector down = new PVector(0, 1);
PVector up = new PVector(0, -1);

boolean isOppositeDir(PVector v1, PVector v2) {
  return v1.x==-v2.x&&v1.y==-v2.y;
}

class Snake {

  PVector location;
  PVector speed;
  PVector head;
  PVector[] body;
  int unit,total;

  Snake(int u, int x, int y) {
    unit = u;
    total=1;
    location = new PVector(x, y).mult(unit);
    body = new PVector[(width/unit)*(height/unit)];
    head = location;
    body[0]=head;
    speed = new PVector(1, 0).mult(unit);
  }

  void changeDir(PVector s) {
    if (!isOppositeDir(speed, s.mult(unit))) {
      speed = s;
    }
  }

  void update() {
    for(int i=total;i>0;i--){
     body[i] = body[i-1].copy();
    }
    location.add(speed);
  }

  void show() {
    for (int i=0;i<total;i++) {
      if(i==0){
        fill(0,255,0);
      }else{
       fill(20,180,20); 
      }
      rect(body[i].x, body[i].y, unit, unit);
    }
  }
  
  void grow(){
    body[total]=body[total-1].copy();
    total++;
  }

  boolean eat(Food food) {
    if (head.x+unit==food.location.x&&head.y==food.location.y&&speed.x==unit) {
      return true;
    } else if (head.x==food.location.x+food.unit&&head.y==food.location.y&&speed.x==-unit) {
      return true;
    } else if (head.x==food.location.x&&head.y+unit==food.location.y&&speed.y==unit) {
      return true;
    } else if (head.x==food.location.x&&head.y==food.location.y+food.unit&&speed.y==-unit) {
      return true;
    }
    return false;
  }
  
  void die(){
    for(int i=1;i<total;i++){
     body[i]=null; 
    }
    total=1;
    location.mult(0);
  }
 
 boolean checkCollision(){
  for(int i=0;i<total;i++){
    if(head.x==body[i].x&&head.y+unit==body[i].y&&speed.y==unit){
      return true;
    }else if(head.x==body[i].x+unit&&head.y==body[i].y&&speed.x==-unit){
     return true; 
    }else if(head.x+unit==body[i].x&&head.y==body[i].y&&speed.x==unit){
     return true; 
    }else if(head.x==body[i].x&&head.y==body[i].y+unit&&speed.y==-unit){
     return true; 
    }
  }
  if((head.x+unit>=width&&speed.x==unit)||(head.x<=0&&speed.x==-unit)){
   return true; 
  }else if((head.y<=0&&speed.y==-unit)||(head.y+unit>=height&&speed.y==unit)){
   return true;
  }
  return false; 
 }
}
