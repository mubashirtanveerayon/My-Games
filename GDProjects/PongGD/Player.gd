extends KinematicBody2D

export var speed = 400

func _physics_process(delta):
	var velocity = Vector2.ZERO
	
	if Input.is_action_pressed("ui_up"):
		velocity.y -= speed 
	elif Input.is_action_pressed("ui_down"):
		velocity.y += speed 
		
	move_and_slide(velocity)
	
func reset():
	position.x = 1040
	position.y = get_viewport().size.y/2
