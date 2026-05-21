@file:Suppress("ktlint:standard:no-wildcard-imports", "unused")
@file:OptIn(NMS::class)

package de.jarox.paplin.command

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import de.jarox.paplin.annotation.NMS
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.*
import net.minecraft.commands.arguments.coordinates.BlockPosArgument
import net.minecraft.commands.arguments.coordinates.Coordinates
import net.minecraft.commands.arguments.coordinates.RotationArgument
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.commands.arguments.selector.EntitySelector
import net.minecraft.resources.Identifier
import java.util.UUID

/**
 * Adds a player selector argument (single player).
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.playerArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, EntitySelector>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, EntitySelector> = argument(name, EntityArgument.player(), builder)

/**
 * Adds a player selector argument (multiple players).
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.playersArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, EntitySelector>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, EntitySelector> = argument(name, EntityArgument.players(), builder)

/**
 * Adds an entity selector argument (single entity).
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.entityArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, EntitySelector>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, EntitySelector> = argument(name, EntityArgument.entity(), builder)

/**
 * Adds an entity selector argument (multiple entities).
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.entitiesArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, EntitySelector>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, EntitySelector> = argument(name, EntityArgument.entities(), builder)

/**
 * Adds a dimension argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.dimensionArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, Identifier>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, Identifier> = argument(name, DimensionArgument.dimension(), builder)

/**
 * Adds a block position argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.blockPosArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, Coordinates>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, Coordinates> = argument(name, BlockPosArgument.blockPos(), builder)

/**
 * Adds a Vec3 argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.vec3Argument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, Coordinates>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, Coordinates> = argument(name, Vec3Argument.vec3(), builder)

/**
 * Adds a rotation argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.rotationArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, Coordinates>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, Coordinates> = argument(name, RotationArgument.rotation(), builder)

/**
 * Adds an angle argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.angleArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, AngleArgument.SingleAngle>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, AngleArgument.SingleAngle> = argument(name, AngleArgument.angle(), builder)

/**
 * Adds an identifier (resource location) argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.identifierArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, Identifier>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, Identifier> = argument(name, IdentifierArgument.id(), builder)

/**
 * Adds a game profile argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.gameProfileArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, GameProfileArgument.Result>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, GameProfileArgument.Result> = argument(name, GameProfileArgument.gameProfile(), builder)

/**
 * Adds a UUID argument.
 */
inline fun ArgumentBuilder<CommandSourceStack, *>.uuidArgument(
    name: String,
    builder: RequiredArgumentBuilder<CommandSourceStack, UUID>.() -> Unit = {},
): RequiredArgumentBuilder<CommandSourceStack, UUID> = argument(name, UuidArgument.uuid(), builder)
