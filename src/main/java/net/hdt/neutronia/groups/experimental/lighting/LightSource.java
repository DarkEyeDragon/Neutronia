package net.hdt.neutronia.groups.experimental.lighting;

import net.hdt.neutronia.groups.experimental.features.ColoredLights;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LightSource {

	public final World world;
	public final BlockPos pos;
	public final IBlockState state;
	public final byte brightness;

	byte[][][] incidences = null;

	public LightSource(World world, BlockPos pos, IBlockState state, int brightness) {
		this.world = world;
		this.pos = pos;
		this.state = state;
		this.brightness = (byte) brightness;
	}

	public void newFrame() {
		incidences = null;
	}

	public int getIndidence(BlockPos checkPos) {
		int dist = manhattanDistance(checkPos); 
		if(dist >= brightness)
			return 0;
		
		if(ColoredLights.simulateTravel) {
			if(incidences == null)
				computeIncidences();
			
			int[] coords = posToIndex(checkPos);
			return getIncidenceAtCoords(coords);
		}
		
		return 15 - dist;
	}

	public boolean isValid(World world) {
		return world.isBlockLoaded(pos) && world.getBlockState(pos).equals(state);
	}
	
	final int manhattanDistance(BlockPos target) {
		return Math.abs(pos.getX() - target.getX()) + Math.abs(pos.getY() - target.getY()) + Math.abs(pos.getZ() - target.getZ());
	}
	
	void computeIncidences() {
		int size = brightness * 2 + 1;
		incidences = new byte[size][size][size];

		Edge[] edges = new Edge[size * size * 2];
		Edge[] edges2 = new Edge[edges.length];

		edges[0] = new Edge(pos, brightness);

		while(edges[0] != null) {
			int newIndex = 0;

			for(int i = 0; i < edges.length; i++) {
				Edge edge = edges[i];
				if(edge == null)
					break;

				for(EnumFacing face : EnumFacing.VALUES) {
					Edge next = edge.next(world, face);
					if(next != null) {
						int[] coords = posToIndex(next.pos);
						byte curr = getIncidenceAtCoords(coords);
						if(next.light > curr) {
							pushByteToCoords(coords, next.light);
							if(newIndex >= edges2.length)
								return;

							edges2[newIndex++] = next;
						}
					}
				}
			}

			Edge[] tempEdges = edges;
			edges2[newIndex] = null;
			edges = edges2;
			edges2 = tempEdges;
		}
	}

	int[] posToIndex(BlockPos target) {
		int xdiff = pos.getX() - target.getX() + brightness;
		int ydiff = pos.getY() - target.getY() + brightness;
		int zdiff = pos.getZ() - target.getZ() + brightness;
		
		int[] coords = new int[] { xdiff, ydiff, zdiff };
		return coords;
	}

	byte getIncidenceAtCoords(int[] coords) {
		return (byte) (incidences[coords[0]][coords[1]][coords[2]]);
	}

	void pushByteToCoords(int[] coords, byte b) {
		incidences[coords[0]][coords[1]][coords[2]] = (byte) b;
	}


	@Override
	public boolean equals(Object obj) {
		return obj == this || pos.equals(obj) || (obj != null && (obj instanceof LightSource && ((LightSource) obj).pos.equals(pos)));
	}

	private static class Edge {

		final BlockPos pos;
		final byte light;

		private Edge(BlockPos pos, byte light) {
			this.pos = pos;
			this.light = light;
		}

		Edge next(IBlockAccess world, EnumFacing face) {
			BlockPos nextPos = pos.offset(face);
			byte opacity = (byte) world.getBlockState(nextPos).getLightOpacity(world, nextPos);
			if(opacity < 0)
				return null;

			byte nextLight = (byte) (light - opacity - 1);
			if(nextLight <= 0)
				return null;

			return new Edge(nextPos, nextLight);
		}

	}

}
