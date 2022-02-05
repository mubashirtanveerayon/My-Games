extends Area2D

var damage = 100

var speed = 1000

var from_enemy_ship: bool

var velocity = Vector2.ZERO

func _init_(_from_enemy_ship):
	from_enemy_ship = _from_enemy_ship
	$AnimatedSprite.flip_v = from_enemy_ship
	if from_enemy_ship:
		$AnimatedSprite.animation = "enemy"
		velocity = Vector2(0,1)
	else:
		$AnimatedSprite.animation = "player"
		velocity = Vector2(0,-1)
	velocity = velocity.normalized()

func _process(delta):
	position += velocity * speed * delta

func _on_VisibilityNotifier2D_screen_exited():
	queue_free()


func _on_Bullet_body_entered(body):
	if from_enemy_ship:
		if body.name == "Player":
			body.health = body.health-damage
			queue_free()


func _on_Bullet_area_entered(area):
	if not from_enemy_ship:
		if "Enemy" in area.name:
			area.health -= damage
			queue_free()
			
