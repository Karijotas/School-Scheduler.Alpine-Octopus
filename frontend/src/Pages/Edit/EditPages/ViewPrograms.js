import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import { Button, Dropdown, Icon, Input, Pagination, Table } from 'semantic-ui-react'



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewPrograms() {

    const [activeItem, setActiveItem] = useState('');

    const [programs, setPrograms] = useState([]);

    const fetchPrograms = async () => {
        fetch('/api/v1/programs')
            .then(response => response.json())
            .then(jsonResponse => setPrograms(jsonResponse));
    };

    const removeProgram = (id) => {
        fetch('/api/v1/programs/' + id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        }).then(fetchPrograms);
    }


    useEffect(() => {
        fetchPrograms();
    }, []);
 
 

    return (<div id='programs'>
        <Input placeholder='Filtruoti pagal pavadinimą' />
        
        

        <Button icon labelPosition='left' primary href='#/create' className='controls'><Icon name='database'/>Kurti naują programą</Button>




        <Table selectable >
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Programos aprašymas</Table.HeaderCell>                                       
                    <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                {programs.map(program => (

                    <Table.Row key={program.id}>
                        <Table.Cell>{program.name}</Table.Cell>
                        <Table.Cell>{program.description}</Table.Cell>
                        <Table.Cell>{program.modifiedDate}</Table.Cell>
                        <Table.Cell collapsing>
                             <Button basic primary compact icon='eye' title='Peržiūrėti' active={activeItem === program.id} onClick={console.log('program/' +program.id)}></Button>
                            <Button basic color='black' compact icon='trash alternate' onClick={() => removeProgram(program.id)}></Button>

                        </Table.Cell>
                    </Table.Row>
                ))}


            </Table.Body>
           
        </Table>

        <Pagination 
            defaultActivePage={1}
            firstItem={programs.firstItem}
            lastItem={programs.lastItem}
            pointing
            totalPages={3}
        />
    </div>
    )
}