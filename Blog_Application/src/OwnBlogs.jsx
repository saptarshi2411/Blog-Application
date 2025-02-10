import React, { useState, useEffect } from 'react';
import { Container, Card, Button, ListGroup, Image } from 'react-bootstrap';

const OwnBlogs = () => {
  const [blogs, setBlogs] = useState([]);
  const token = localStorage.getItem('jwtToken');

  useEffect(() => {
    const fetchBlogs = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/ownBlogs', {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });
        const data = await response.json();
        setBlogs(data);
      } catch (error) {
        console.error('Error fetching blogs:', error.message);
      }
    };

    if (token) {
      fetchBlogs();
    }
  }, [token]);

  const deleteBlog = async (randomId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/blogs/${randomId}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(`Failed to delete blog: ${response.status} ${errorMessage}`);
      }

      setBlogs((prevBlogs) => prevBlogs.filter((blog) => blog.randomId !== randomId));
    } catch (error) {
      console.error('Error deleting blog:', error.message);
    }
  };

  return (
    <Container className="mt-5">
      <h2 className="mb-4">Your Blogs</h2>
      <ListGroup variant="flush">
        {blogs.map((blog) => (
          <Card key={blog.id} className="mb-3 shadow-sm">
            <Card.Body>
              <Card.Title>Tile - {blog.title}</Card.Title>
              <Card.Text>Content - {blog.content}</Card.Text>
              {blog.imageData && (
                <Image
                  src={`data:image/jpeg;base64,${blog.imageData}`}
                  alt={blog.title}
                  className="mt-3"
                  style={{ width: '70%', height: 'auto' }}
                />
              )}
              <br/>
              <Button
                variant="danger"
                className="mt-3"
                onClick={() => deleteBlog(blog.randomId)}
              >
                Delete
              </Button>
            </Card.Body>
          </Card>
        ))}
      </ListGroup>
    </Container>
  );
};

export default OwnBlogs;
