////////////////////////////////////////////////////////////////////////////////
// sudokuki - C++ graphical sudoku game                                       //
// Copyright (C) 2007-2009 Sylvain Vedrenne                                   //
//                                                                            //
// This program is free software; you can redistribute it and/or              //
// modify it under the terms of the GNU General Public License                //
// as published by the Free Software Foundation; either version 2             //
// of the License, or (at your option) any later version.                     //
//                                                                            //
// This program is distributed in the hope that it will be useful,            //
// but WITHOUT ANY WARRANTY; without even the implied warranty of             //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              //
// GNU General Public License for more details.                               //
//                                                                            //
// You should have received a copy of the GNU General Public License along    //
// with this program; if not, write to the Free Software Foundation, Inc.,    //
// 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.              //
////////////////////////////////////////////////////////////////////////////////
#ifndef GENERATOR_H_
#define GENERATOR_H_

#ifdef __cplusplus
extern "C" {
#endif
extern int grid_generate ( int seed, int requestedRatingMin, int requestedRatingMax, int** grid, int* rating, int** grid_with_clues );
#ifdef __cplusplus
}
#endif

#endif /*GENERATOR_H_*/
