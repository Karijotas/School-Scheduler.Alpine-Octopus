import React, { useEffect, useState } from 'react'
import { useHref, useParams } from 'react-router-dom';
import { Button, Input, Table } from 'semantic-ui-react';
import MainMenu from '../../../Components/MainMenu'
import './EditObject.css';


const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function EditObject() {
    const params = useParams();
    const listUrl = useHref('/edit');


    const [error, setError] = useState();


    const [groups, setGroups] = useState({
        name: '',
        schoolYear: '',
        studentAmount: '',
        program: '',
        modifiedDate:'',
    });

    useEffect(() => {
        fetch('/api/v1/groups/' + params.id)
            .then(response => response.json())
            .then(setGroups);
    }, []);


    const updateGroups = () => {
        fetch('/api/v1/groups/' + params.id, {
            method: 'PATCH',
            headers: JSON_HEADERS,
            body: JSON.stringify(groups)
        }).then(result => {
            if (!result.ok) {
                setError('Update failed');
            } else {
                setError();
            }
        }) 
        .then(() => window.location = listUrl);
    };

    const updateProperty = (property, event) => {
        setGroups({
            ...groups,
            [property]: event.target.value
        });
    };


    const removeGroup = (id) => {
        fetch('/api/v1/groups/' + params.id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        })
        .then(() => window.location = listUrl);
    }




    return (<div>
        <MainMenu />
        <div className='ui container' id='table'>
             <Table celled collapsing compact color='violet'>
            <Table.Header >
                <Table.Row  >
                    <Table.HeaderCell >ID</Table.HeaderCell>
                    <Table.HeaderCell >Grupės pavadinimas "Teams"</Table.HeaderCell>
                    <Table.HeaderCell>Mokslo Metai</Table.HeaderCell>
                    <Table.HeaderCell>Studentų Kiekis</Table.HeaderCell>
                    <Table.HeaderCell>Programa</Table.HeaderCell>
                   <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>

                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                </Table.Row>
            </Table.Header>

            <Table.Body>
                <Table.Row key={params.id} >
                    <Table.Cell collapsing>{groups.id}</Table.Cell>
                    <Table.Cell><Input value={groups.name} onChange={(e) => updateProperty('name', e)}/> </Table.Cell>
                    <Table.Cell><Input value={groups.schoolYear} onChange={(e) => updateProperty('schoolYear', e)}/></Table.Cell>
                    <Table.Cell><Input value={groups.studentAmount} onChange={(e) => updateProperty('studentAmount', e)}/></Table.Cell>
                    <Table.Cell><Input value={groups.program} onChange={(e) => updateProperty('program', e)}/></Table.Cell>
                    <Table.Cell > {groups.modifiedDate}  </Table.Cell>

                    <Table.Cell collapsing> <Button primary onClick={updateGroups}>Save</Button><Button negative onClick={removeGroup}>Delete</Button>
                </Table.Cell>

              
                </Table.Row>  
               
            </ Table.Body >

        </Table>
        </div>
       



    </div>
    )
}

