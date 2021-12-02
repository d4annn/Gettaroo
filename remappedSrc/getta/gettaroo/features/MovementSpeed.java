package getta.gettaroo.features;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class MovementSpeed {

    public static Vec3d checkMovement(ClientPlayerEntity entity){

        Vec3d velocity = entity.getVelocity();
        float x = 0;
        float y = 0;
        float z = 0;
        //subir la x e z
        if(entity.input.jumping){
            y = 0.2f;
        }
        if(entity.input.sneaking){
            y = -0.2f;
        }
        if(entity.input.pressingForward){
            x = 0.2f;
        }

        return new Vec3d(velocity.x , velocity.y + y, velocity.z);
    }
}
