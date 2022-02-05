extends KinematicBody2D

onready var speed = 500

var velocity = Vector2.ZERO


func _physics_process(delta):
	$Sprite.flip_h = velocity.x < 0
	
	var collision_object = move_and_collide(velocity * speed * delta)

	if collision_object:
		velocity = velocity.bounce(collision_object.normal)
		$CollisionSound.play()
		
	
func stop_ball():
	position = Vector2(540,320)
	speed = 0

func reset():
	speed = 500
	randomize()
	velocity.x = [-1,1][randi() % 2]
	velocity.y = [-0.8,0.8][randi() % 2]
	
