package feature.calendar

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class ExoPlayerWrapper : Serializable {
    @Transient
    var player: ExoPlayer? = null

    fun writeObject(out: ObjectOutputStream) {
        out.defaultWriteObject()
        out.writeObject(player?.currentPosition)
    }

    fun Context.readObject(inStream: ObjectInputStream) {
        inStream.defaultReadObject()
        val position = inStream.readObject() as Long
        player = ExoPlayer.Builder(this).build()
        player?.seekTo(position)
    }
}