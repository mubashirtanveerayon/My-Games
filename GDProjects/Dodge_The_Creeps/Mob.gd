extends RigidBody2D

var min_speed = 150
var max_speed = 250

func _ready():
	randomize()
	var mob_types = $AnimatedSprite.frames.get_animation_names()
	$AnimatedSprite.animation = mob_types[randi() % mob_types.size()]
	$AnimatedSprite.play()


func _on_VisibilityNotifier2D_screen_exited():
	queue_free()
