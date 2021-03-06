/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2006 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitri Polivaev and others.
 *
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*
 * Created on 16.05.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package freemind.view.mindmapview

import java.awt.Shape
import java.awt.geom.PathIterator
import java.awt.geom.QuadCurve2D
import java.awt.geom.Rectangle2D

internal object PathBBox {
    fun getBBox(s: Shape): Rectangle2D {
        var first = true
        val bounds = DoubleArray(4)
        val coords = DoubleArray(6)
        var curx = 0.0
        var cury = 0.0
        var movx = 0.0
        var movy = 0.0
        var cpx0: Double
        var cpy0: Double
        var cpx1: Double
        var cpy1: Double
        var endx: Double
        var endy: Double
        val pi = s.getPathIterator(null)
        while (!pi.isDone) {
            when (pi.currentSegment(coords)) {
                PathIterator.SEG_MOVETO -> {
                    run {
                        curx = coords[0]
                        movx = curx
                    }
                    run {
                        cury = coords[1]
                        movy = cury
                    }
                    if (first) {
                        bounds[2] = curx
                        bounds[0] = bounds[2]
                        bounds[3] = cury
                        bounds[1] = bounds[3]
                        first = false
                    } else {
                        accum(bounds, curx, cury)
                    }
                }
                PathIterator.SEG_LINETO -> {
                    curx = coords[0]
                    cury = coords[1]
                    accum(bounds, curx, cury)
                }
                PathIterator.SEG_QUADTO -> {
                    cpx0 = coords[0]
                    cpy0 = coords[1]
                    endx = coords[2]
                    endy = coords[3]
                    var t = findQuadZero(curx, cpx0, endx)
                    if (t > 0 && t < 1) {
                        accumQuad(bounds, t, curx, cury, cpx0, cpy0, endx, endy)
                    }
                    t = findQuadZero(cury, cpy0, endy)
                    if (t > 0 && t < 1) {
                        accumQuad(bounds, t, curx, cury, cpx0, cpy0, endx, endy)
                    }
                    curx = endx
                    cury = endy
                    accum(bounds, curx, cury)
                }
                PathIterator.SEG_CUBICTO -> {
                    cpx0 = coords[0]
                    cpy0 = coords[1]
                    cpx1 = coords[2]
                    cpy1 = coords[3]
                    endx = coords[4]
                    endy = coords[5]
                    var num = findCubicZeros(coords, curx, cpx0, cpx1, endx)
                    run {
                        var i = 0
                        while (i < num) {
                            accumCubic(
                                bounds, coords[i], curx, cury, cpx0, cpy0, cpx1,
                                cpy1, endx, endy
                            )
                            i++
                        }
                    }
                    num = findCubicZeros(coords, cury, cpy0, cpy1, endy)
                    var i = 0
                    while (i < num) {
                        accumCubic(
                            bounds, coords[i], curx, cury, cpx0, cpy0, cpx1,
                            cpy1, endx, endy
                        )
                        i++
                    }
                    curx = endx
                    cury = endy
                    accum(bounds, curx, cury)
                }
                PathIterator.SEG_CLOSE -> {
                    // Original starting point already included
                    curx = movx
                    cury = movy
                }
            }
            pi.next()
        }
        return Rectangle2D.Double(
            bounds[0], bounds[1],
            bounds[2] -
                bounds[0],
            bounds[3] - bounds[1]
        )
    }

    private fun accum(bounds: DoubleArray, x: Double, y: Double) {
        bounds[0] = Math.min(bounds[0], x)
        bounds[1] = Math.min(bounds[1], y)
        bounds[2] = Math.max(bounds[2], x)
        bounds[3] = Math.max(bounds[3], y)
    }

    private fun findQuadZero(cur: Double, cp: Double, end: Double): Double {
        // The polynomial form of the Quadratic is:
        // eqn[0] = cur;
        // eqn[1] = cp + cp - cur - cur;
        // eqn[2] = cur - cp - cp + end;
        // Since we want the derivative, we can calculate it in one step:
        // deriv[0] = cp + cp - cur - cur;
        // deriv[1] = 2 * (cur - cp - cp + end);
        // Since we really want the zero, we can calculate that in one step:
        // zero = -deriv[0] / deriv[1]
        return -(cp + cp - cur - cur) / (2.0 * (cur - cp - cp + end))
    }

    private fun accumQuad(
        bounds: DoubleArray,
        t: Double,
        curx: Double,
        cury: Double,
        cpx0: Double,
        cpy0: Double,
        endx: Double,
        endy: Double
    ) {
        val u = 1 - t
        val x = curx * u * u + 2.0 * cpx0 * t * u + endx * t * t
        val y = cury * u * u + 2.0 * cpy0 * t * u + endy * t * t
        accum(bounds, x, y)
    }

    private fun findCubicZeros(
        zeros: DoubleArray,
        cur: Double,
        cp0: Double,
        cp1: Double,
        end: Double
    ): Int {
        // The polynomial form of the Cubic is:
        // eqn[0] = cur;
        // eqn[1] = (cp0 - cur) * 3.0;
        // eqn[2] = (cp1 - cp0 - cp0 + cur) * 3.0;
        // eqn[3] = end + (cp0 - cp1) * 3.0 - cur;
        // Since we want the derivative, we can calculate it in one step:
        zeros[0] = (cp0 - cur) * 3.0
        zeros[1] = (cp1 - cp0 - cp0 + cur) * 6.0
        zeros[2] = (end + (cp0 - cp1) * 3.0 - cur) * 3.0
        val num = QuadCurve2D.solveQuadratic(zeros)
        var ret = 0
        for (i in 0 until num) {
            val t = zeros[i]
            if (t > 0 && t < 1) {
                zeros[ret] = t
                ret++
            }
        }
        return ret
    }

    private fun accumCubic(
        bounds: DoubleArray,
        t: Double,
        curx: Double,
        cury: Double,
        cpx0: Double,
        cpy0: Double,
        cpx1: Double,
        cpy1: Double,
        endx: Double,
        endy: Double
    ) {
        val u = 1 - t
        val x = curx * u * u * u + 3.0 * cpx0 * t * u * u + (
            3.0 * cpx1 * t
                * t * u
            ) + endx * t * t * t
        val y = cury * u * u * u + 3.0 * cpy0 * t * u * u + (
            3.0 * cpy1 * t
                * t * u
            ) + endy * t * t * t
        accum(bounds, x, y)
    }
}
