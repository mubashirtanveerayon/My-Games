extends Area2D
signal hit

var speed = 400.0

onready var screen_size = get_viewport_rect().size

func _process(delta):
	
	var velocity = Vector2.ZERO
	
	if Input.is_action_pressed("ui_up"):
		velocity.y -= 1
	if Input.is_action_pressed("ui_down"):
		velocity.y += 1
		
	if Input.is_action_pressed("ui_left"):
		velocity.x -= 1
	if Input.is_action_pressed("ui_right"):
		velocity.x += 1
		
	velocity = velocity.normalized()
	
	if velocity.x != 0:
		$AnimatedSprite.animation = "right"
		$AnimatedSprite.flip_h = velocity.x < 0
		$AnimatedSprite.flip_v = false

	if velocity.y != 0:
		$AnimatedSprite.animation = "up"
		$AnimatedSprite.flip_v = velocity.y > 0
	
	if velocity.length() > 0:
		$AnimatedSprite.play()
	else:
		$AnimatedSprite.stop()
		
	position += velocity * speed * delta
	
	position.x = clamp(position.x,0,screen_size.x)
	position.y = clamp(position.y,0,screen_size.y)

func start(new_position):
	position = new_position
	show()
	$CollisionShape2D.disabled = false

func _on_Player_body_entered(body):
	hide()
	$CollisionShape2D.set_deferred("disable",true)
	emit_signal("hit")
