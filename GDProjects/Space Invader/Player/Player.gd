extends KinematicBody2D

signal dead


export var bullet_scene: PackedScene

var speed = 500

var health = 1000

var refilled_health:bool

const max_health = 2000.0

var shot_from_right: bool = false
var shot_from_left: bool = false

func _ready():
	$RightGunCooldownTimer.start()
	$LeftGunCooldownTimer.start()


func _physics_process(delta):
	var velocity = Vector2.ZERO
	velocity.x = Input.get_action_strength("ui_right") - Input.get_action_strength("ui_left")
	velocity.y = Input.get_action_strength("ui_down") - Input.get_action_strength("ui_up")
	velocity = velocity.normalized()
	
	if velocity.x > 0:
		$AnimatedSprite.animation = "tilt_right"
	elif velocity.x < 0:
		$AnimatedSprite.animation = "tilt_left"
	else:
		$AnimatedSprite.animation = "default"
		if Input.is_action_pressed("ui_accept"):
			if not shot_from_left or not shot_from_right:
				shoot()
					
	move_and_collide(velocity * speed * delta)

	$HealthBar.value = health/max_health * 100

	if health <= 0 and visible:
		dead()

func reset():
	position = get_tree().get_root().find_node("PlayerStartPosition",true,false).global_position
	health = max_health
	$LeftGunCooldownTimer.start()
	$RightGunCooldownTimer.start()
	$CollisionPolygon2D.set_deferred("disabled",false)
	show()
	
func dead():
	$LeftGunCooldownTimer.stop()
	$RightGunCooldownTimer.stop()
	$CollisionPolygon2D.set_deferred("disabled",true)
	hide()
	emit_signal("dead")

func shoot():
	var bullet = bullet_scene.instance()
	bullet._init_(false)
	if not shot_from_right:
		bullet.position = $RightGunPosition.global_position
		shot_from_right = true
	elif not shot_from_left:
		bullet.position = $LeftGunPosition.global_position
		shot_from_left = true
	
	get_parent().add_child(bullet)
	
	$ShootSound.play()
	

func _on_RightGunCooldownTimer_timeout():
	shot_from_right = false


func _on_LeftGunCooldownTimer_timeout():
	shot_from_left = false


