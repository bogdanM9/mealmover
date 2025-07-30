# Google OAuth2 Login Setup

This document explains how to configure and use Google OAuth2 login in the MealMover application.

## Prerequisites

1. A Google Cloud Platform (GCP) project
2. OAuth2 credentials configured in Google Cloud Console

## Google Cloud Setup

### 1. Create OAuth2 Credentials

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Select your project or create a new one
3. Navigate to **APIs & Services** > **Credentials**
4. Click **+ CREATE CREDENTIALS** > **OAuth client ID**
5. Select **Web application** as the application type
6. Configure the OAuth client:
   - **Name**: MealMover App (or any name you prefer)
   - **Authorized JavaScript origins**: 
     - `http://localhost:8080` (for backend)
     - `http://localhost:5173` (for frontend)
   - **Authorized redirect URIs**:
     - `http://localhost:8080/api/auth/oauth2/callback/google`
7. Click **CREATE**
8. Copy the **Client ID** and **Client Secret**

### 2. Configure Environment Variables

Set the following environment variables or add them to your `application.yml`:

```bash
export GOOGLE_CLIENT_ID=your-google-client-id-here
export GOOGLE_CLIENT_SECRET=your-google-client-secret-here
```

Or update `src/main/resources/application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your-google-client-id-here
            client-secret: your-google-client-secret-here
```

## API Endpoints

### Initiate Google Login

```http
GET /api/auth/oauth2/login/google
```

This endpoint redirects the user to Google's OAuth2 authorization server.

### OAuth2 Callback

```http
GET /api/auth/oauth2/callback/google
```

This is handled automatically by Spring Security. After successful authentication:
- If user exists: logs them in
- If new user: creates a new account with CLIENT role
- Sets JWT token as cookie
- Redirects to frontend

## Frontend Integration

### Redirect User to Google Login

```javascript
// Redirect to Google OAuth2 login
window.location.href = 'http://localhost:8080/api/auth/oauth2/login/google';
```

### Handle OAuth2 Success/Error

Create pages to handle the OAuth2 flow results:

```javascript
// Success page: /auth/oauth2/success
// The user is now authenticated with JWT cookie set

// Error page: /auth/oauth2/error?error=...
// Handle the authentication error
```

## User Flow

1. User clicks "Login with Google" button
2. Frontend redirects to `/api/auth/oauth2/login/google`
3. User is redirected to Google's authorization server
4. User authorizes the application
5. Google redirects back to `/api/auth/oauth2/callback/google`
6. Backend processes the OAuth2 response:
   - Extracts user info (email, name)
   - Creates new user or finds existing one
   - Generates JWT token
   - Sets token as HTTP-only cookie
7. User is redirected to frontend success page
8. Frontend can now make authenticated requests using the JWT cookie

## Security Features

- **No Circular Dependencies**: OAuth2 services are separate from existing auth services
- **JWT Integration**: OAuth2 users get the same JWT tokens as regular users
- **Role Assignment**: New OAuth2 users automatically get CLIENT role
- **Existing User Support**: OAuth2 login works with existing email accounts

## Error Handling

The application handles various OAuth2 errors:
- Invalid credentials
- User denies authorization
- Network errors
- User info extraction failures

All errors redirect to the frontend error page with appropriate error messages.

## Development Notes

- OAuth2 users are created with placeholder phone numbers (required field)
- OAuth2 users don't have passwords (they use OAuth2 for authentication)
- The implementation preserves the existing JWT-based authentication system
- All existing endpoints continue to work unchanged