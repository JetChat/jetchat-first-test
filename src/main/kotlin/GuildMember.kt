class GuildMember(val guild: Guild, val user: User) {
	val id get() = user.id
	val username get() = user.username
	val tag get() = user.tag
}
