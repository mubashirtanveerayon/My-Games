extends KinematicBody2D


var speed = 400
var ball

func _ready():
	ball = get_parent().find_node("Ball") 

func _physics_process(delta):
	
	var velocity = Vector2.ZERO
	
	velocity.y += get_opponent_direction()
		
	move_and_slide(velocity * speed)

func get_opponent_direction():
	if abs(position.y - ball.position.y) > 30:
		if position.y > ball.position.y: return -1
		else: return 1
	else: return 0


func reset():
	position.x = 40
	position.y = get_viewport().size.y/2
