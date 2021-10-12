package com.gonzales.mark.n_puzzle

class ShuffleUtil {
    companion object {
        fun getValidShuffledState(
            puzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): Pair<ArrayList<Int>, Int> {
            puzzleState.shuffle()

            /*
             * If the 8-puzzle is not solvable, that is, it has an odd number of inversions,
             * swap a pair of tiles to change the parity and, thus, guarantee solvability.
             */
            if (!isSolvable(puzzleState, blankTileMarker)) {
                swapTiles(puzzleState, blankTileMarker)
            }

            return Pair(puzzleState, puzzleState.indexOf(blankTileMarker))
        }

        private fun countInversions(puzzleState: ArrayList<Int>, blankTileMarker: Int): Int {
            var numInversions = 0

            for (i in 0 until puzzleState.size - 1) {
                for (j in i + 1 until puzzleState.size) {
                    if (!isBlankTile(i, puzzleState, blankTileMarker)
                        && !isBlankTile(j, puzzleState, blankTileMarker)
                        && puzzleState[i] > puzzleState[j]
                    ) {
                        numInversions++
                    }
                }
            }

            return numInversions
        }

        private fun isSolvable(puzzleState: ArrayList<Int>, blankTileMarker: Int): Boolean {
            return countInversions(puzzleState, blankTileMarker) % 2 == 0
        }

        private fun swapTiles(puzzleState: ArrayList<Int>, blankTileMarker: Int) {
            var position = 0
            while (isBlankTile(position, puzzleState, blankTileMarker)
                || isBlankTile(position + 1, puzzleState, blankTileMarker)
            ) {
                position++
            }

            /* Swap the two tiles via Kotlin's also idiom. */
            puzzleState[position] = puzzleState[position + 1].also {
                puzzleState[position + 1] = puzzleState[position]
            }
        }

        private fun isBlankTile(
            position: Int,
            puzzleState: ArrayList<Int>,
            blankTileMarker: Int
        ): Boolean {
            return puzzleState[position] == blankTileMarker
        }
    }
}