extends Area2D
signal player_hit
signal enemy_passed
signal enemy_killed

var offset = 50

var speed = 50

var velocity: Vector2

export var bullet_scene: PackedScene

var player

onready var parent = get_parent()

var health = 500

onready var screen_rect = get_viewport().size

func _ready():
	randomize()
	player = get_tree().get_root().find_node("Player",true,false)
	velocity.y = RandomNumberGenerator.new().randf_range(0.5,2) 
	var enemy_type = $AnimatedSprite.frames.get_animation_names()
	$AnimatedSprite.animation = enemy_type[randi() % enemy_type.size()]
	var cooldown_time = rand_range(1,2)
	$ShootTimer.wait_time = cooldown_time
	connect("player_hit",parent,"_on_Player_dead")
	connect("enemy_passed",parent,"_on_Enemy_passed")
	connect("enemy_killed",parent,"_on_Enemy_killed")
	connect("player_hit",player,"dead")
	
func spawn(territory_flag):
	randomize()
	var random = RandomNumberGenerator.new()
	if territory_flag:
		global_position.x = rand_range(offset,screen_rect.x/2.0 - offset)
		global_position.y = rand_range(-screen_rect.y/2.0+offset,- offset)
	else:
		global_position.x = rand_range(screen_rect.x/2.0 + offset,screen_rect.x - offset)
		global_position.y = rand_range(-screen_rect.y+offset,-screen_rect.y/2.0 - offset)

func _process(delta):
	position += velocity * speed * delta
	if health <= 0 and speed != 0:
		dead()
	$HealthBar.value = health/500.0 * 100

func dead():
	emit_signal("enemy_killed")
	$CollisionShape2D.set_deferred("disabled",true)
	$AnimatedSprite.hide()
	$ShootTimer.stop()
	$HealthBar.hide()
	$SelfDeleteTimer.start()
	speed = 0
	show_bonus()

func _on_VisibilityNotifier2D_screen_exited():
	$SelfDeleteTimer.start()
	speed = 0


func _on_ShootTimer_timeout():
	var bullet = bullet_scene.instance()
	bullet._init_(true)
	bullet.position = $GunPosition.global_position
	get_parent().add_child(bullet)


func _on_Enemy_body_entered(body):
	if body.name == "Player":
		emit_signal("player_hit")
		queue_free()
	elif body.name == "Floor":
		emit_signal("enemy_passed")
		show_penalty()
		$CollisionShape2D.set_deferred("disabled",true)
		$AnimatedSprite.hide()
		$ShootTimer.stop()
		$HealthBar.hide()
		$SelfDeleteTimer.start()
		speed = 0

func show_penalty():
	$PenaltyLabel.text = "-5"
	$PenaltyLabel.add_color_override("font_color",Color(255,0,0))
	

func show_bonus():
	$PenaltyLabel.text = "+5"
	$PenaltyLabel.add_color_override("font_color",Color(0,255,0))
	

func _on_VisibilityNotifier2D_screen_entered():
	$ShootTimer.start()
	$CollisionShape2D.set_deferred("disabled",false)


func _on_SelfDeleteTimer_timeout():
	queue_free()
