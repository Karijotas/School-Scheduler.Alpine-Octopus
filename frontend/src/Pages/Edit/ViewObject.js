import React from 'react'
import { ViewGroups } from './EditPages/ViewGroups'
import { ViewRooms } from './EditPages/ViewRooms'
import { ViewPrograms } from './EditPages/ViewPrograms'
import { ViewSubjects } from './EditPages/ViewSubjects'
import { ViewModules } from './EditPages/ViewModules'
import { ViewTeachers } from './EditPages/ViewTeachers'




// Here we should pass the selection as props, and then map the values from Backend to the tables
// I've just done here a simple if else that is only for example, not how it should work.
// Change/Edit this section to suit your case. 
// Best is to create a new file for your implementation based on this one, and later we will refactor and merge the best options
// One could just pass the newly created file here, after return
export function ObjectList(props) {
    if (props === 'groups') {
        return (
            <ViewGroups />

        )
    }


    if (props === 'subjects') {

        return (
            <ViewSubjects />
        )
    }




    if (props === 'teachers') {
        return (
            <p>
                <ViewTeachers/>
            </p>

        )
    }
    if (props === 'shifts') {
        return (
            <p>
                Pamainos:
            </p>

        )
    }
    if (props === 'rooms') {
        return (
            <ViewRooms />

        )
    }
    if (props === 'program') {
        return (
            <ViewPrograms />

        )
    }
    if (props === 'modules') {

        return (
                <ViewModules/>
        )
    }
}

