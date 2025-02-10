import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Button, Container, Alert } from 'react-bootstrap';
import OtherBlogs from './OtherBlogs';

const AddBlog = () => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [category, setCategory] = useState('');
    const [image, setImage] = useState(null);
    const [message, setMessage] = useState('');
    
    const navigate = useNavigate();

    const handleImageChange = (e) => {
        setImage(e.target.files[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const token = localStorage.getItem('jwtToken');
        if (!token) {
            setMessage('User is not authenticated');
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        formData.append('content', content);
        formData.append('category', category);
        if (image) {
            formData.append('image', image);
        }

        try {
            const response = await fetch('http://localhost:8080/api/addBlog', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                body: formData,
            });

            if (response.ok) {
                setMessage('Blog added successfully!');
                setTitle('');
                setContent('');
                setCategory('');
                setImage(null);
            } else {
                setMessage('Failed to add blog.');
            }
        } catch (error) {
            console.error('Error:', error);
            setMessage('An error occurred while adding the blog.');
        }
    };

    const goToMyBlog = () => {
        navigate("/ownBlog");
    };

    return (
        <Container className="mt-5">
            <h2 className="mb-4 text-center">Add a New Blog</h2>
            {message && (
                <Alert variant={message.includes('successfully') ? 'success' : 'danger'}>
                    {message}
                </Alert>
            )}
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="formTitle" className="mb-3">
                    <Form.Label>Title</Form.Label>
                    <Form.Control
                        type="text"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="formContent" className="mb-3">
                    <Form.Label>Content</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={5}
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="formCategory" className="mb-3">
                    <Form.Label>Category</Form.Label>
                    <Form.Control
                        type="text"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="formImage" className="mb-3">
                    <Form.Label>Upload Image</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                    />
                </Form.Group>

                <Button variant="primary" type="submit" className="mb-3">
                    Add Blog
                </Button>
            </Form>

            <OtherBlogs />

            <Button variant="dark" onClick={goToMyBlog} className="mt-3">
                Own Blogs
            </Button>
        </Container>
    );
};

export default AddBlog;
